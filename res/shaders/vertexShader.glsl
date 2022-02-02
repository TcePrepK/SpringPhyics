#version 450 core

in vec3 position;
in vec3 color;

uniform mat4 modelMatrix;
uniform mat4 projectionViewMatrix;

out vec3 passColor;

void main(void) {
    gl_Position = projectionViewMatrix * modelMatrix * vec4(position, 1.0);

    passColor = color;
}