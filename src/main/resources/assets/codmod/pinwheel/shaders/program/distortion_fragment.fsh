#version 150
uniform sampler2D DiffuseSampler0;
in vec2 texCoord;
out vec4 fragColor;
void main() {
    float distortionAmount = 0.3;
    float time = 20;
    vec2 distortCoord = texCoord + vec2(sin(texCoord.y * 12 + time) * distortionAmount, 0.0);
    vec4 color = texture(DiffuseSampler0, distortCoord);
    fragColor = vec4(color.rgb, 0.25);
}