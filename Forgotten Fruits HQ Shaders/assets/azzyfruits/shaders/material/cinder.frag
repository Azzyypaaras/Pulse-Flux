#include canvas:shaders/api/fragment.glsl
#include canvas:shaders/lib/math.glsl
#include canvas:shaders/api/world.glsl

// holds our noise coordinates from the vertex shader
varying vec2 v_noise_uv;

// The name of this method is special - Canvas will call it for each fragment associated with your shader 
void cv_startFragment(inout cv_FragmentData fragData) {
	// modify appearance where stone texture is lighter in color
	if (cv_luminance(fragData.spriteColor.rgb) > 0) {
		fragData.emissivity = 1.0;
		fragData.ao = false;
		fragData.diffuse = false;
	}
}