#version 460

layout (points) in;

layout (location = 0) in vec4 in_color[];
layout (location = 1) in vec2 in_pos[];
layout (location = 2) in vec2 in_dir[];
layout (location = 3) in float in_intensity[];

layout (triangle_strip, max_vertices = 4) out;

layout (location = 0) out vec4 out_color;
layout (location = 1) out vec2 out_pos;

void main()
{
    vec2 dir = in_dir[0];
    vec2 pos = in_pos[0];
    float intensity = in_intensity[0];
    vec4 color = in_color[0];
    float size = intensity;



    EndPrimitive();
}