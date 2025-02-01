package net.rimrim.rimmod.chem.correlation;


import net.rimrim.rimmod.chem.correlation.type.base.BaseForm;

public class Forms {

    /*
    Psat(T) = A*exp[(B - T/C) * (T/(D + T))]
     */
    public static BaseForm ARDEN_BUCK = new BaseForm(
            4, 1,
            (F) -> {
                float A = F.c(0), B = F.c(1), C = F.c(2), D = F.c(3);
                float T = F.p(0);
                return A * (float) Math.exp((B - T / C) * (T / (D + T)));
            }
    );

    /*
    Psat(T) = exp[A - B/(T + C)]
     */
    public static BaseForm ANTOINE = new BaseForm(
            3, 1,
            (F) -> {
                float A = F.c(0), B = F.c(1), C = F.c(2);
                float T = F.p(0);
                return (float) Math.exp(A - B/(T + C));
            }
    );

    /*
    Psat(Tr) = exp[A + B ln(Tr) + C (ln Tr)^2 + D (ln Tr)^4 + E Tr^5]
    More complicated
     */
    public static BaseForm REFRIGERANT_PSAT = new BaseForm(
            5, 2,
            (F) -> {
                float A = F.c(0), B = F.c(1), C = F.c(2), D = F.c(3), E = F.c(4);
                float T = F.p(0), Tc = F.p(1);
                float Tr = T/Tc;
                float Tr5 = Tr * Tr * Tr * Tr * Tr;
                double lnTr = Math.log(Tr);
                double lnTr2 = lnTr * lnTr;
                double lnTr4 = lnTr2 * lnTr2;
                return (float) Math.exp(A + B * lnTr + C * lnTr2 + D * lnTr4 + E*Tr5);
            }
    );

    /*
    τ = 1 - Tr
    Pr(Tr) = exp[ (1/Tr)*( a1(τ) + a2(τ)^1.5 + a3(τ)^3 + a4(τ)^3.5 + a5(τ)^4 + a6(τ)^7.5)  ]
     */
//    public static BaseForm IAPS_PSAT = new BaseForm(
//
//
//    );


}
