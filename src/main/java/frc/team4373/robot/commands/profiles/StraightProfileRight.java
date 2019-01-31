package frc.team4373.robot.commands.profiles;

public class StraightProfileRight implements MotionProfile {
    private double[][] points = new double[][]{
            {0.019047, 0.0, 50.0},
            {0.019047, 0.0, 50.0},
            {0.221401, 4.047079, 50.0},
            {0.621381, 7.999609, 50.0},
            {1.135642, 10.285211, 50.0},
            {1.764182, 12.570814, 50.0},
            {2.507003, 14.856416, 50.0},
            {3.364104, 17.142019, 50.0},
            {4.335485, 19.427621, 50.0},
            {5.421146, 21.713224, 50.0},
            {6.621088, 23.998826, 50.0},
            {7.935309, 26.284429, 50.0},
            {9.363811, 28.570031, 50.0},
            {10.906592, 30.855634, 50.0},
            {12.563654, 33.141236, 50.0},
            {14.334996, 35.426839, 50.0},
            {16.220618, 37.712441, 50.0},
            {18.22052, 39.998044, 50.0},
            {20.334703, 42.283646, 50.0},
            {22.563165, 44.569249, 50.0},
            {24.905908, 46.854851, 50.0},
            {27.36293, 49.140454, 50.0},
            {29.934233, 51.426056, 50.0},
            {32.619816, 53.711659, 50.0},
            {35.419679, 55.997261, 50.0},
            {38.333822, 58.282864, 50.0},
            {41.362246, 60.568466, 50.0},
            {44.504949, 62.854069, 50.0},
            {47.742886, 64.758738, 50.0},
            {51.037963, 65.901539, 50.0},
            {54.352087, 66.282473, 50.0},
            {57.66621, 66.282473, 50.0},
            {60.980334, 66.282473, 50.0},
            {64.294457, 66.282473, 50.0},
            {67.608581, 66.282473, 50.0},
            {70.922705, 66.282473, 50.0},
            {74.236828, 66.282473, 50.0},
            {77.550952, 66.282473, 50.0},
            {80.865076, 66.282473, 50.0},
            {84.179199, 66.282473, 50.0},
            {87.493323, 66.282473, 50.0},
            {90.807447, 66.282473, 50.0},
            {94.12157, 66.282473, 50.0},
            {97.435694, 66.282473, 50.0},
            {100.749817, 66.282473, 50.0},
            {104.063941, 66.282473, 50.0},
            {107.378065, 66.282473, 50.0},
            {110.692188, 66.282473, 50.0},
            {114.006312, 66.282473, 50.0},
            {117.320436, 66.282473, 50.0},
            {120.634559, 66.282473, 50.0},
            {123.948683, 66.282473, 50.0},
            {127.262806, 66.282473, 50.0},
            {130.57693, 66.282473, 50.0},
            {133.891054, 66.282473, 50.0},
            {137.205177, 66.282473, 50.0},
            {140.519301, 66.282473, 50.0},
            {143.833425, 66.282473, 50.0},
            {147.147548, 66.282473, 50.0},
            {150.461672, 66.282473, 50.0},
            {153.775795, 66.282473, 50.0},
            {157.089919, 66.282473, 50.0},
            {160.404043, 66.282473, 50.0},
            {163.718166, 66.282473, 50.0},
            {167.03229, 66.282473, 50.0},
            {170.346414, 66.282473, 50.0},
            {173.660537, 66.282473, 50.0},
            {176.974661, 66.282473, 50.0},
            {180.288785, 66.282473, 50.0},
            {183.602908, 66.282473, 50.0},
            {186.917032, 66.282473, 50.0},
            {190.231155, 66.282473, 50.0},
            {193.545279, 66.282473, 50.0},
            {196.859403, 66.282473, 50.0},
            {200.173526, 66.282473, 50.0},
            {203.48765, 66.282473, 50.0},
            {206.801774, 66.282473, 50.0},
            {210.115897, 66.282473, 50.0},
            {213.430021, 66.282473, 50.0},
            {216.744144, 66.282473, 50.0},
            {220.058268, 66.282473, 50.0},
            {223.372392, 66.282473, 50.0},
            {226.686515, 66.282473, 50.0},
            {230.000639, 66.282473, 50.0},
            {233.314763, 66.282473, 50.0},
            {236.628886, 66.282473, 50.0},
            {239.94301, 66.282473, 50.0},
            {243.257133, 66.282473, 50.0},
            {246.571257, 66.282473, 50.0},
            {249.885381, 66.282473, 50.0},
            {253.199504, 66.282473, 50.0},
            {256.513628, 66.282473, 50.0},
            {259.827752, 66.282473, 50.0},
            {263.141875, 66.282473, 50.0},
            {266.455999, 66.282473, 50.0},
            {269.770123, 66.282473, 50.0},
            {273.084246, 66.282473, 50.0},
            {276.39837, 66.282473, 50.0},
            {279.712493, 66.282473, 50.0},
            {283.026617, 66.282473, 50.0},
            {286.340741, 66.282473, 50.0},
            {289.654864, 66.282473, 50.0},
            {292.968988, 66.282473, 50.0},
            {296.283112, 66.282473, 50.0},
            {299.591792, 66.17361, 50.0},
            {302.87054, 65.574951, 50.0},
            {306.081261, 64.214425, 50.0},
            {309.191305, 62.200893, 50.0},
            {312.18707, 59.915291, 50.0},
            {315.068554, 57.629688, 50.0},
            {317.835759, 55.344086, 50.0},
            {320.488683, 53.058483, 50.0},
            {323.027327, 50.772881, 50.0},
            {325.451691, 48.487278, 50.0},
            {327.761775, 46.201676, 50.0},
            {329.957578, 43.916073, 50.0},
            {332.039102, 41.630471, 50.0},
            {334.006345, 39.344868, 50.0},
            {335.859308, 37.059266, 50.0},
            {337.597992, 34.773663, 50.0},
            {339.222395, 32.488061, 50.0},
            {340.732518, 30.202458, 50.0},
            {342.12836, 27.916856, 50.0},
            {343.409923, 25.631253, 50.0},
            {344.577206, 23.345651, 50.0},
            {345.630208, 21.060048, 50.0},
            {346.56893, 18.774446, 50.0},
            {347.393372, 16.488843, 50.0},
            {348.103534, 14.203241, 50.0},
            {348.699416, 11.917638, 50.0},
            {349.181018, 9.632036, 50.0},
            {349.54834, 7.346433, 50.0},
            {349.801381, 5.060831, 50.0},
            {349.945586, 2.884091, 50.0},
            {350.005443, 1.197147, 50.0},
            {350.019047, 0.272071, 50.0},
            {350.019047, 0.0, 50.0}
    };

    /**
     * Gets the number of points in the profile.
     * @return the number of points in the profile.
     */
    public int getNumPoints() {
        return 137;
    }

    /**
     * Gets the points in the profile.
     * @return the points in the profile.
     */
    public double[][] getPoints() {
        return points;
    }
}
