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
    return pow(clamp((1.0 - distance / size(intensity)), 0.0, 1.0), 2);
}

void main()
{
    float distance = length(in_pos);
    float lightness = lightness(distance, in_intensity);

    out_color = in_color * lightness;
}