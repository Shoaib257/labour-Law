public class ImportentProcessing {
    public static String replaceEngDigitsWithBnDigits(String engDigits){
        StringBuilder str=new StringBuilder();
        Character[] bnDigits={'০','১','২','৩','৪','৫','৬','৭','৮','৯'};
        for(int i=0;i<engDigits.length();i++){
            if(engDigits.charAt(i)==0){
                str.append(bnDigits[0]);
            }else if(engDigits.charAt(i)==1){
                str.append(bnDigits[1]);
            }else if(engDigits.charAt(i)==2){
                str.append(bnDigits[2]);
            }else if(engDigits.charAt(i)==3){
                str.append(bnDigits[3]);
            }else if(engDigits.charAt(i)==4){
                str.append(bnDigits[4]);
            }else if(engDigits.charAt(i)==5){
                str.append(bnDigits[5]);
            }else if(engDigits.charAt(i)==6){
                str.append(bnDigits[6]);
            }else if(engDigits.charAt(i)==7){
                str.append(bnDigits[7]);
            }else if(engDigits.charAt(i)==8){
                str.append(bnDigits[8]);
            }else if(engDigits.charAt(i)==9){
                str.append(bnDigits[9]);
            }else{
                str.append(engDigits.charAt(i));

            }
        }
        return str.toString();
    }

    //internal executions are written bellow

}
