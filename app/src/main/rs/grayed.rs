#include "pragma.rsh"

rs_allocation gIn;// 输入图像的 allocation 对象
rs_allocation gOut;// 输出图像的 allocation 对象
rs_script gScript;// RenderScript 对象

// 一种常用的加权平均灰度化的权重值
const static float3 gMonoMult = {0.299f, 0.587f, 0.114f};

// rs 的初始化函数，只会被调用一次，可以不实现
void init(){
    rsDebug("===init ======",0);
}

// rs 的核心函数，每一个像素都会执行这个函数
void root(const uchar4 *v_in, uchar4 *v_out) {
    rsDebug("===root wiz 2 params",0);// debug info
    float4 f4 = rsUnpackColor8888(*(v_in));// 获得 输入allocation 中当前点的 RGBA 值
    float3 mono = dot(f4.rgb, gMonoMult);// 灰度运算
    *v_out = rsPackColorTo8888(mono);// 将灰度值写入 输入allocation 的当前对应位置点
}