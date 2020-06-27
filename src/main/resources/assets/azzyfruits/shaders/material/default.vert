#include canvas:shaders/api/vertex.glsl
#include canvas:shaders/lib/face.glsl

varying vec2 v_noise_uv;

void cv_startVertex(inout cv_VertexData data) {
    v_noise_uv = cv_faceUv(data.vertex.xyz, data.normal);
}
