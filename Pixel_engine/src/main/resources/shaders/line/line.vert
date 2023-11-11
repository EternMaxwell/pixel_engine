#version 460

layout(location = 0) in vec2 in_position;
layout(location = 1) in vec4 in_color;

layout(location = 0) out vec4 out_color;

layout(binding = 0) uniform Uniforms {
    mat4 projection;
    mat4 view;
    mat4 model;
} uniforms;

void main() {
    out_color = in_color;
    mat4 mvp = uniforms.projection * uniforms.view * uniforms.model;
    gl_Position = mvp*vec4(in_position, 0.0, 1.0);
}
