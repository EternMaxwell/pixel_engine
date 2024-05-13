#version 460 core

layout(location = 0) in vec4 in_color;
layout(location = 1) in vec2 in_pos;
layout(location = 2) in vec2 in_dir;
layout(location = 3) in float in_intensity;
layout(location = 4) in float in_brightness;

layout(location = 0) out vec4 out_color;

float size(float intensity){
    return intensity;
}

float cal_lightness(float distance, float intensity){
    float ratio = 1 - pow(distance / size(intensity), 1/2.0);
    return clamp(in_brightness * ratio, 0, 1);
}

float dir_lightness(vec2 dir, vec2 pos, float intensity){
    float angle = dir.x;
    float orientation = dir.y;

    vec2 light_facing = normalize(vec2(cos(angle), sin(angle)));
    float facing = dot(light_facing, normalize(pos));
    float lightness = cal_lightness(length(pos), intensity);
    lightness *= clamp((facing - orientation) * orientation / (1 - orientation) + (1 - orientation), 0, 1) * (1 + orientation / 2);
    return lightness;
}

float adjust(float value, float add){
    return (value + add) / (1 + add);
}

void main()
{
    float distance = length(in_pos);
    float lightness = in_brightness;
    lightness *= dir_lightness(in_dir, in_pos, in_intensity);

    out_color = in_color * lightness;
}