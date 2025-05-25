#version 150
in vec3 Position;
in vec2 UV0;
uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
out vec2 texCoord;
void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    texCoord = (Position.xy + vec2(0.5)) / 1.0; // Map [-0.5, 0.5] to [0, 1]
}