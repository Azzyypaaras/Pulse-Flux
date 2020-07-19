#include canvas:shaders/api/fragment.glsl
#include canvas:shaders/lib/math.glsl
#include canvas:shaders/api/world.glsl

// holds our noise coordinates from the vertex shader
varying vec2 v_noise_uv;

void cv_startFragment(inout cv_FragmentData fragData) {

	if (fragData.spriteColor.r > 0.1f) {

		fragData.emissivity = 1.0f;
		fragData.ao = false;
		fragData.diffuse = false;
	}
}