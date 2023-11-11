#version 460

layout(location = 0) in vec2 in_texcoord;
layout(location = 1) in vec4 in_color;

layout(location = 0) out vec4 out_color;

layout(binding = 0) uniform sampler2D tex;

void main() {
    out_color = texture(tex, in_texcoord) * in_color;
}