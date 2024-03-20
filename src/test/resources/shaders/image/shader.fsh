#version 460

layout(location = 0) in vec2 in_tex;

layout(location = 0) out vec4 fragColor;

layout(binding = 0) uniform sampler2D tex;

void main() {
    fragColor = texture(tex, in_tex);
}
