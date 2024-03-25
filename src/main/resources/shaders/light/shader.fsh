#version 460 core

layout(location = 0) in vec4 in_color;
layout(location = 1) in vec2 in_pos;
layout(location = 2) in vec2 in_dir;
layout(location = 3) in float in_intensity;

layout(location = 0) out vec4 out_color;

float size(float intensity){
    return intensity;
}

float lightness(float distance, float intensity){
    return intensity * pow(clamp((1.0 - distance / size(intensity)), 0.0, 1.0), 2);
}

layout(std430, binding = 0) buffer NormalMap{
    vec2 normal[64][64];
} normal_map;

layout(binding = 1) uniform float air_fog;

void main()
{
    float distance = length(in_pos);
    float lightness = lightness(distance, in_intensity);

    out_color = in_color * lightness;
}