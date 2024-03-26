#version 460 core

layout(location = 0) in vec4 in_color;
layout(location = 1) in vec2 in_pos;
layout(location = 2) in vec2 in_dir;
layout(location = 3) in float in_intensity;
layout(location = 4) in vec2 in_real_pos;

layout(location = 0) out vec4 out_color;

float size(float intensity){
    return 2 * intensity;
}

float cal_lightness(float distance, float intensity){
    float ratio = 1 / (distance * distance);
    return intensity * clamp(ratio, 0, 1);
}

layout(std430, binding = 0) buffer NormalMap{
    vec2 normal[64][64];
    float air_fog[64][64];
    bool if_air[64][64];
} normal_map;

void main()
{
    float distance = length(in_pos);
    float lightness;
    ivec2 index = ivec2(in_real_pos * 32 + 32);
    index.x = clamp(index.x, 0, 63);
    index.y = clamp(index.y, 0, 63);

    vec2 normal1 = normal_map.normal[index.x][index.y];
    vec2 normal = normal1 * max(normal1.x, normal1.y) / length(normal1);
    float air_fog = normal_map.air_fog[index.x][index.y];
    bool if_air = normal_map.if_air[index.x][index.y];

    float ref = -normalize(in_pos).x * normal.x - normalize(in_pos).y * normal.y;
//    ref = ref / 2 + 0.5f;
    ref = clamp(ref, 0.0, 1.0);

    lightness = if_air? air_fog: ref;
    lightness *= cal_lightness(distance, in_intensity);

    out_color = in_color * lightness;
}