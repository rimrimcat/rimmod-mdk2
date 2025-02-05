package net.rimrim.rimmod.chem.props;

import net.rimrim.rimmod.chem.correlation.type.UnsetProperty;
import net.rimrim.rimmod.chem.correlation.type.base.IFunction;
import net.rimrim.rimmod.util.ModNativeLoader;
import org.lwjgl.system.Library;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;

public class SpeciesBuilder {
    public String name = "";
    public ChemTags tags = new ChemTags.Builder().build();
    public int color = packColor(0, 0, 0);

    public float molecular_weight;
    public float acentric_factor;
    public float critical_temperature;
    public float critical_pressure;
    public float critical_molar_volume;
    public float critical_compressibility_factor;

    public IFunction solid_density = new UnsetProperty();
    public IFunction liquid_density = new UnsetProperty();
    public IFunction vapor_density = new UnsetProperty();

    public IFunction solid_viscosity = new UnsetProperty();
    public IFunction liquid_viscosity = new UnsetProperty();
    public IFunction vapor_viscosity = new UnsetProperty();

    public IFunction solid_heat_capacity = new UnsetProperty();
    public IFunction liquid_heat_capacity = new UnsetProperty();
    public IFunction vapor_heat_capacity = new UnsetProperty();

    public IFunction solid_thermal_conductivity = new UnsetProperty();
    public IFunction liquid_thermal_conductivity = new UnsetProperty();
    public IFunction vapor_thermal_conductivity = new UnsetProperty();

    public IFunction solid_vapor_pressure = new UnsetProperty();
    public IFunction liquid_vapor_pressure = new UnsetProperty();

    private void loadLib() {
        ModNativeLoader.loadNativeLibrary();

        // Library.loadSystem("openbabel_java", "E:\\CloudStorage\\files\\Scripts\\rimmod-mdk2\\libs\\openbabel_java.dll");
        // Library.loadSystem("openbabel_java", "openbabel_java.dll");
        // TODO: FIX PATH
    }

    private int packColor(int r, int g, int b, int a) {
        int red = r & 0xFF;
        int green = g & 0xFF;
        int blue = b & 0xFF;
        int alpha = a & 0xFF;
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    private int packColor(int r, int g, int b) {
        return this.packColor(r, g, b, 255);
    }

    public SpeciesBuilder fromSmiles(String smiles) {
        this.loadLib();

        OBConversion conv = new OBConversion();
        OBMol mol = new OBMol();
        conv.SetInFormat("smi");
        conv.ReadString(mol, smiles);

        this.molecular_weight = (float) mol.GetMolWt();

        // TODO: Calculate other properties here

        return this;
    }


    public SpeciesBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SpeciesBuilder color(int col) {
        this.color = col;
        return this;
    }

    public SpeciesBuilder color(int r, int g, int b) {
        this.color = packColor(r, g, b);
        return this;
    }

    public SpeciesBuilder color(int r, int g, int b, int a) {
        this.color = packColor(r, g, b, a);
        return this;
    }

    public SpeciesBuilder tags(ChemTags tags) {
        this.tags = tags;
        return this;
    }

    public SpeciesBuilder addMissing() {
        // TODO: Infer missing stuff?

        return this;
    }

    // CONSTANTS
    public SpeciesBuilder MW(float molecular_weight) {
        return this.molecular_weight(molecular_weight);
    }

    public SpeciesBuilder MW(double molecular_weight) {
        return this.molecular_weight((float) molecular_weight);
    }

    public SpeciesBuilder molecular_weight(float molecular_weight) {
        this.molecular_weight = molecular_weight;
        return this;
    }

    public SpeciesBuilder w(float accentric_factor) {
        return this.acentric_factor(accentric_factor);
    }

    public SpeciesBuilder w(double accentric_factor) {
        return this.acentric_factor((float) accentric_factor);
    }

    public SpeciesBuilder acentric_factor(float acentric_factor) {
        this.acentric_factor = acentric_factor;
        return this;
    }

    public SpeciesBuilder Tc(float critical_temperature) {
        return this.critical_temperature(critical_temperature);
    }

    public SpeciesBuilder Tc(double critical_temperature) {
        return this.critical_temperature((float) critical_temperature);
    }

    public SpeciesBuilder critical_temperature(float critical_temperature) {
        this.critical_temperature = critical_temperature;
        return this;
    }

    public SpeciesBuilder Pc(float critical_pressure) {
        return this.critical_pressure(critical_pressure);
    }

    public SpeciesBuilder Pc(double critical_pressure) {
        return this.critical_pressure((float) critical_pressure);
    }

    public SpeciesBuilder critical_pressure(float critical_pressure) {
        this.critical_pressure = critical_pressure;
        return this;
    }

    public SpeciesBuilder Vc(float critical_molar_volume) {
        return this.critical_molar_volume(critical_molar_volume);
    }

    public SpeciesBuilder Vc(double critical_molar_volume) {
        return this.critical_molar_volume((float) critical_molar_volume);
    }

    public SpeciesBuilder critical_molar_volume(float critical_molar_volume) {
        this.critical_molar_volume = critical_molar_volume;
        return this;
    }

    public SpeciesBuilder Zc(float critical_compressibility_factor) {
        return this.critical_compressibility_factor(critical_compressibility_factor);
    }

    public SpeciesBuilder Zc(double critical_compressibility_factor) {
        return this.critical_compressibility_factor((float) critical_compressibility_factor);
    }

    public SpeciesBuilder critical_compressibility_factor(float critical_compressibility_factor) {
        this.critical_compressibility_factor = critical_compressibility_factor;
        return this;
    }

    public SpeciesBuilder solid_density(IFunction solid_density) {
        this.solid_density = solid_density;
        return this;
    }

    public SpeciesBuilder liquid_density(IFunction liquid_density) {
        this.liquid_density = liquid_density;
        return this;
    }

    public SpeciesBuilder vapor_density(IFunction vapor_density) {
        this.vapor_density = vapor_density;
        return this;
    }

    public SpeciesBuilder solid_viscosity(IFunction solid_viscosity) {
        this.solid_viscosity = solid_viscosity;
        return this;
    }

    public SpeciesBuilder liquid_viscosity(IFunction liquid_viscosity) {
        this.liquid_viscosity = liquid_viscosity;
        return this;
    }

    public SpeciesBuilder vapor_viscosity(IFunction vapor_viscosity) {
        this.vapor_viscosity = vapor_viscosity;
        return this;
    }

    public SpeciesBuilder solid_heat_capacity(IFunction solid_heat_capacity) {
        this.solid_heat_capacity = solid_heat_capacity;
        return this;
    }

    public SpeciesBuilder liquid_heat_capacity(IFunction liquid_heat_capacity) {
        this.liquid_heat_capacity = liquid_heat_capacity;
        return this;
    }

    public SpeciesBuilder vapor_heat_capacity(IFunction vapor_heat_capacity) {
        this.vapor_heat_capacity = vapor_heat_capacity;
        return this;
    }

    public SpeciesBuilder solid_thermal_conductivity(IFunction solid_thermal_conductivity) {
        this.solid_thermal_conductivity = solid_thermal_conductivity;
        return this;
    }

    public SpeciesBuilder liquid_thermal_conductivity(IFunction liquid_thermal_conductivity) {
        this.liquid_thermal_conductivity = liquid_thermal_conductivity;
        return this;
    }

    public SpeciesBuilder vapor_thermal_conductivity(IFunction vapor_thermal_conductivity) {
        this.vapor_thermal_conductivity = vapor_thermal_conductivity;
        return this;
    }

    public SpeciesBuilder solid_vapor_pressure(IFunction solid_vapor_pressure) {
        this.solid_vapor_pressure = solid_vapor_pressure;
        return this;
    }

    public SpeciesBuilder liquid_vapor_pressure(IFunction liquid_vapor_pressure) {
        this.liquid_vapor_pressure = liquid_vapor_pressure;
        return this;
    }


}

