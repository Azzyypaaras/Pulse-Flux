#include canvas:shaders/api/fragment.glsl
#include canvas:shaders/lib/math.glsl
#include canvas:shaders/api/world.glsl

varying vec2 v_noise_uv;

void cv_startFragment(inout cv_FragmentData fragData) {
	float time = cv_renderSeconds();
	if (fragData.spriteColor.r > 0.45 && !(cv_luminance(fragData.spriteColor.rgb) > 0.65 && cv_luminance(fragData.spriteColor.rgb) < 0.7) || (fragData.spriteColor.b < 0.3 && fragData.spriteColor.r > 0.25)) {
		fragData.emissivity = abs(sin(sqrt(time)))+1.0;
		fragData.ao = false;
		fragData.diffuse = false;
	}
}