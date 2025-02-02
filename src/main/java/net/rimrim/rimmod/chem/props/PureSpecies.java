package net.rimrim.rimmod.chem.props;

import net.rimrim.rimmod.chem.correlation.type.base.IFunction;
import net.rimrim.rimmod.chem.correlation.type.UnsetProperty;
import org.lwjgl.system.Library;
import org.openbabel.OBConversion;
import org.openbabel.OBMol;


public class PureSpecies extends PureDependentProperty {
    // SPECIES IDENTITY
    public final String name;
    public final ChemTags tags;

    // CONSTANT PROPERTIES
    public final float molecular_weight;
    public final float accentric_factor;
    public final float critical_temperature;
    public final float critical_pressure;
    public final float critical_molar_volume;
    public final float critical_compressibility_factor;

    // FUNCTIONS
    private final IFunction solid_density;
    private final IFunction liquid_density;
    private final IFunction vapor_density;

    private final IFunction solid_viscosity;
    private final IFunction liquid_viscosity;
    private final IFunction vapor_viscosity;

    private final IFunction solid_heat_capacity;
    private final IFunction liquid_heat_capacity;
    private final IFunction vapor_heat_capacity;

    private final IFunction solid_thermal_conductivity;
    private final IFunction liquid_thermal_conductivity;
    private final IFunction vapor_thermal_conductivity;

    private final IFunction solid_vapor_pressure;
    private final IFunction liquid_vapor_pressure;


    protected PureSpecies(String name, ChemTags tags, float molecularWeight, float accentricFactor, float criticalTemperature, float criticalPressure, float criticalMolarVolume, float criticalCompressibilityFactor, IFunction solidDensity, IFunction liquidDensity, IFunction vaporDensity, IFunction solidViscosity, IFunction liquidViscosity, IFunction vaporViscosity, IFunction solidHeatCapacity, IFunction liquidHeatCapacity, IFunction vaporHeatCapacity, IFunction solidThermalConductivity, IFunction liquidThermalConductivity, IFunction vaporThermalConductivity, IFunction solidVaporPressure, IFunction liquidVaporPressure) {
        this.name = name;
        this.tags = tags;
        molecular_weight = molecularWeight;
        accentric_factor = accentricFactor;
        critical_temperature = criticalTemperature;
        critical_pressure = criticalPressure;
        critical_molar_volume = criticalMolarVolume;
        critical_compressibility_factor = criticalCompressibilityFactor;
        solid_density = solidDensity;
        liquid_density = liquidDensity;
        vapor_density = vaporDensity;
        solid_viscosity = solidViscosity;
        liquid_viscosity = liquidViscosity;
        vapor_viscosity = vaporViscosity;
        solid_heat_capacity = solidHeatCapacity;
        liquid_heat_capacity = liquidHeatCapacity;
        vapor_heat_capacity = vaporHeatCapacity;
        solid_thermal_conductivity = solidThermalConductivity;
        liquid_thermal_conductivity = liquidThermalConductivity;
        vapor_thermal_conductivity = vaporThermalConductivity;
        solid_vapor_pressure = solidVaporPressure;
        liquid_vapor_pressure = liquidVaporPressure;
    }


    public float MW() {
        return this.molecular_weight;
    }

    public float w() {
        return this.accentric_factor;
    }

    public float Tc() {
        return this.critical_temperature;
    }

    public float Pc() {
        return this.critical_pressure;
    }

    public float Vc() {
        return this.critical_molar_volume;
    }

    public float Zc() {
        return this.critical_compressibility_factor;
    }

    // Alias
    public float molecular_weight() {
        return this.MW();
    }

    public float accentric_factor() {
        return this.w();
    }

    public float critical_temperature() {
        return this.Tc();
    }

    public float critical_pressure() {
        return this.Pc();
    }

    public float critical_molar_volume() {
        return this.Vc();
    }

    public float critical_compressibility_factor() {
        return this.Zc();
    }

    // Builder
    public static class Builder {
        private String name = "";
        private String atoms;
        private ChemTags tags = new ChemTags.Builder().build();

        private float molecular_weight;
        private float accentric_factor;
        private float critical_temperature;
        private float critical_pressure;
        private float critical_molar_volume;
        private float critical_compressibility_factor;

        private IFunction solid_density = new UnsetProperty();
        private IFunction liquid_density = new UnsetProperty();
        private IFunction vapor_density = new UnsetProperty();

        private IFunction solid_viscosity = new UnsetProperty();
        private IFunction liquid_viscosity = new UnsetProperty();
        private IFunction vapor_viscosity = new UnsetProperty();

        private IFunction solid_heat_capacity = new UnsetProperty();
        private IFunction liquid_heat_capacity = new UnsetProperty();
        private IFunction vapor_heat_capacity = new UnsetProperty();

        private IFunction solid_thermal_conductivity = new UnsetProperty();
        private IFunction liquid_thermal_conductivity = new UnsetProperty();
        private IFunction vapor_thermal_conductivity = new UnsetProperty();

        private IFunction solid_vapor_pressure = new UnsetProperty();
        private IFunction liquid_vapor_pressure = new UnsetProperty();

        private void loadLib() {
            Library.loadSystem("openbabel_java", "E:\\CloudStorage\\files\\Scripts\\rimmod-mdk2\\libs\\openbabel_java.dll");
            // TODO: FIX PATH
        }

        public Builder fromSmiles(String smiles) {
            this.loadLib();

            OBConversion conv = new OBConversion();
            OBMol mol = new OBMol();
            conv.SetInFormat("smi");
            conv.ReadString(mol, smiles);

            this.molecular_weight = (float) mol.GetMolWt();

            // TODO: Calculate other properties here

            return this;
        }


        // BORING STUFF
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder tags(ChemTags tags) {
            this.tags = tags;
            return this;
        }

        // CONSTANTS
        public Builder MW(float molecular_weight) {
            return this.molecular_weight(molecular_weight);
        }

        public Builder MW(double molecular_weight) {
            return this.molecular_weight((float) molecular_weight);
        }

        public Builder molecular_weight(float molecular_weight) {
            this.molecular_weight = molecular_weight;
            return this;
        }

        public Builder w(float accentric_factor) {
            return this.accentric_factor(accentric_factor);
        }

        public Builder w(double accentric_factor) {
            return this.accentric_factor((float) accentric_factor);
        }

        public Builder accentric_factor(float accentric_factor) {
            this.accentric_factor = accentric_factor;
            return this;
        }

        public Builder Tc(float critical_temperature) {
            return this.critical_temperature(critical_temperature);
        }

        public Builder Tc(double critical_temperature) {
            return this.critical_temperature((float) critical_temperature);
        }

        public Builder critical_temperature(float critical_temperature) {
            this.critical_temperature = critical_temperature;
            return this;
        }

        public Builder Pc(float critical_pressure) {
            return this.critical_pressure(critical_pressure);
        }

        public Builder Pc(double critical_pressure) {
            return this.critical_pressure((float) critical_pressure);
        }

        public Builder critical_pressure(float critical_pressure) {
            this.critical_pressure = critical_pressure;
            return this;
        }

        public Builder Vc(float critical_molar_volume) {
            return this.critical_molar_volume(critical_molar_volume);
        }

        public Builder Vc(double critical_molar_volume) {
            return this.critical_molar_volume((float) critical_molar_volume);
        }

        public Builder critical_molar_volume(float critical_molar_volume) {
            this.critical_molar_volume = critical_molar_volume;
            return this;
        }

        public Builder Zc(float critical_compressibility_factor) {
            return this.critical_compressibility_factor(critical_compressibility_factor);
        }

        public Builder Zc(double critical_compressibility_factor) {
            return this.critical_compressibility_factor((float) critical_compressibility_factor);
        }

        public Builder critical_compressibility_factor(float critical_compressibility_factor) {
            this.critical_compressibility_factor = critical_compressibility_factor;
            return this;
        }

        public Builder solid_density(IFunction solid_density) {
            this.solid_density = solid_density;
            return this;
        }

        public Builder liquid_density(IFunction liquid_density) {
            this.liquid_density = liquid_density;
            return this;
        }

        public Builder vapor_density(IFunction vapor_density) {
            this.vapor_density = vapor_density;
            return this;
        }

        public Builder solid_viscosity(IFunction solid_viscosity) {
            this.solid_viscosity = solid_viscosity;
            return this;
        }

        public Builder liquid_viscosity(IFunction liquid_viscosity) {
            this.liquid_viscosity = liquid_viscosity;
            return this;
        }

        public Builder vapor_viscosity(IFunction vapor_viscosity) {
            this.vapor_viscosity = vapor_viscosity;
            return this;
        }

        public Builder solid_heat_capacity(IFunction solid_heat_capacity) {
            this.solid_heat_capacity = solid_heat_capacity;
            return this;
        }

        public Builder liquid_heat_capacity(IFunction liquid_heat_capacity) {
            this.liquid_heat_capacity = liquid_heat_capacity;
            return this;
        }

        public Builder vapor_heat_capacity(IFunction vapor_heat_capacity) {
            this.vapor_heat_capacity = vapor_heat_capacity;
            return this;
        }

        public Builder solid_thermal_conductivity(IFunction solid_thermal_conductivity) {
            this.solid_thermal_conductivity = solid_thermal_conductivity;
            return this;
        }

        public Builder liquid_thermal_conductivity(IFunction liquid_thermal_conductivity) {
            this.liquid_thermal_conductivity = liquid_thermal_conductivity;
            return this;
        }

        public Builder vapor_thermal_conductivity(IFunction vapor_thermal_conductivity) {
            this.vapor_thermal_conductivity = vapor_thermal_conductivity;
            return this;
        }

        public Builder solid_vapor_pressure(IFunction solid_vapor_pressure) {
            this.solid_vapor_pressure = solid_vapor_pressure;
            return this;
        }

        public Builder liquid_vapor_pressure(IFunction liquid_vapor_pressure) {
            this.liquid_vapor_pressure = liquid_vapor_pressure;
            return this;
        }

        public PureSpecies build() {
            // Read Tags and add other stuff


            return new PureSpecies(
                    name,
                    tags,
                    molecular_weight,
                    accentric_factor,
                    critical_temperature,
                    critical_pressure,
                    critical_molar_volume,
                    critical_compressibility_factor,
                    solid_density, liquid_density, vapor_density,
                    solid_viscosity, liquid_viscosity, vapor_viscosity,
                    solid_heat_capacity, liquid_heat_capacity, vapor_heat_capacity,
                    solid_thermal_conductivity, liquid_thermal_conductivity, vapor_thermal_conductivity,
                    solid_vapor_pressure, liquid_vapor_pressure
            );
        }

    }

}
