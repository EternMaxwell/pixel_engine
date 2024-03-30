#version 460

layout(location = 0) in vec2 in_tex;
layout (location = 1) in vec4 in_color;

layout(location = 0) out vec4 fragColor;

layout(binding = 0) uniform sampler2D tex;

void main() {
    fragColor = texture(tex, in_tex) * in_color;
}
