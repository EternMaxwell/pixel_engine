#version 460 core

layout (points) in;

layout (location = 0) in vec4 in_color[];
layout (location = 1) in vec2 in_dir[];
layout (location = 2) in float in_intensity[];
layout (location = 3) in float in_max_lightness[];

layout (triangle_strip, max_vertices = 4) out;

layout (location = 0) out vec4 out_color;
layout (location = 1) out vec2 out_pos;
layout (location = 2) out vec2 out_dir;
layout (location = 3) out float out_intensity;
layout (location = 4) out float out_max_lightness;

layout (binding = 0) uniform Uniform{
    mat4 projection;
    mat4 view;
    mat4 model;
} uniforms;

float size(float intensity){
    return intensity;
}

void main()
{
    mat4 mvp = uniforms.projection * uniforms.view * uniforms.model;

    vec2 dir = in_dir[0];
    vec2 pos = gl_in[0].gl_Position.xy;
    float intensity = in_intensity[0];
    vec4 color = in_color[0];
    float size = size(intensity);

    out_color = color;
    out_pos = vec2(-size, -size);
    out_dir = dir;
    out_intensity = intensity;
    out_max_lightness = in_max_lightness[0];
    gl_Position = mvp * vec4(pos + out_pos, 0.0, 1.0);
    EmitVertex();

    out_color = color;
    out_pos = vec2(size, -size);
    out_dir = dir;
    out_intensity = intensity;
    out_max_lightness = in_max_lightness[0];
    gl_Position = mvp * vec4(pos + out_pos, 0.0, 1.0);
    EmitVertex();

    out_color = color;
    out_pos = vec2(-size, size);
    out_dir = dir;
    out_intensity = intensity;
    out_max_lightness = in_max_lightness[0];
    gl_Position = mvp * vec4(pos + out_pos, 0.0, 1.0);
    EmitVertex();

    out_color = color;
    out_pos = vec2(size, size);
    out_dir = dir;
    out_intensity = intensity;
    out_max_lightness = in_max_lightness[0];
    gl_Position = mvp * vec4(pos + out_pos, 0.0, 1.0);
    EmitVertex();

    EndPrimitive();
}