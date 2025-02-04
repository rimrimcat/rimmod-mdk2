package net.rimrim.rimmod.chem.container.shape;

public class ContainerBuilder {

    private float inner_volume = 0;
    private float inner_length = 0;
    private float thickness = 2 / 16f;
    private Shape shape = Shape.CUBIC;


    public ContainerBuilder() {

    }

    public ContainerBuilder length(float length) {
        this.inner_length = length;
        return this;
    }

    public ContainerBuilder volume(float volume) {
        this.inner_volume = volume;
        return this;
    }

    public ContainerBuilder thickness(float thickness) {
        this.thickness = thickness;
        return this;
    }

    public IContainerShape build() {
        switch (shape) {
            case CUBIC: {
                if (inner_length == 0) inner_length = (float) Math.cbrt(inner_volume);
                return new Cubic(inner_length, thickness);
            }
            default: {
                return new Cubic(1);
            }
        }
    }


}
