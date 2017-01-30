#version 400 core

in vec3 position;
in vec2 uvCoords;

out vec2 pass_textureCoords;

uniform mat4 combinedMatrix;

void main(void) {

	gl_Position = combinedMatrix * vec4(position.xyz, 1.0);
	pass_textureCoords = uvCoords;
}