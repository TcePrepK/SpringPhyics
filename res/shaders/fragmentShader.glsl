#version 450 core

in vec3 passColor;

uniform vec2 resolution;

out vec3 outColor;

void main(void) {
    outColor = passColor;
}