#include canvas:shaders/api/vertex.glsl
#include canvas:shaders/lib/face.glsl

// sends noise coordinates from the vertex shader
varying vec2 v_noise_uv;

// The name of this method is special - Canvas will call it for each vertex associated with your shader 
void cv_startVertex(inout cv_VertexData data) {
	// 2D noise coordinates are derived from world geometry using a Canvas library function
	v_noise_uv = cv_faceUv(data.vertex.xyz, data.normal);
}