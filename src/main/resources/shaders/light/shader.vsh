#version 460 core

layout(location = 0) in vec2 in_pos;
layout(location = 1) in vec2 in_dir;
layout(location = 2) in vec4 in_color;
layout(location = 3) in float in_intensity;

layout(location = 0) out vec4 out_color;
layout(location = 1) out vec2 out_dir;
layout(location = 2) out float out_intensity;

void main(){
    out_dir = in_dir;
    out_intensity = in_intensity;
    out_color = in_color;
    gl_Position = vec4(in_pos, 0.0, 1.0);
}