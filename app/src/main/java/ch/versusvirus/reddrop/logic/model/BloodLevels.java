package ch.versusvirus.reddrop.logic.model;

public enum BloodLevels{
    CriticallyLow,
    Critical,
    Low,
    Normal,
    High;

    public static int getPercent(BloodLevels  level){
        switch (level){
            case CriticallyLow:
                return 2;
            case Critical:
                return 10;
            case Low:
                return 25;
            case Normal:
                return 65;
            case High:
                return 90;
            default:
                throw new RuntimeException("BloodLevel percent not yet implemented!");
        }
    }
}
