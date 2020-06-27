#include canvas:shaders/api/fragment.glsl
#include canvas:shaders/lib/math.glsl

varying vec2 v_noise_uv;

void cv_startFragment(inout cv_FragmentData fragData) {
    fragData.emissivity = 1.0;
    fragData.diffuse = false;
    fragData.ao = false;
}