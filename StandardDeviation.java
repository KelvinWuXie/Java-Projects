public class StandardDeviation {

  public static void main(String [] args) {

    Double result;
    Double [] arr = new Double [args.length];
    
    for(int i = 0; i < args.length; i++) {
      arr[i] = Double.parseDouble(args[i]);
    }
    
    System.out.print("The standard deviation of ");
    for (int j = 0; j < args.length -1; j++) {
      System.out.print( arr[j] + ", ") ;
    }
    System.out.print("and " + arr[args.length - 1] + " is " ); 
    result = stddev(arr);
    System.out.println(result);
  } // end of main method
  public static double stddev(Double... arr) { 
    double sum = 0.0;
    double standardDeviation = 0.0;
    int length = arr.length;
    
    for (double num : arr) {
      sum += num;
    } 
    
    double mean = sum/ length;
    
    for (double num: arr) {
      standardDeviation += Math.pow(num - mean, 2);
    }
    return Math.sqrt(standardDeviation/length);
  } // end of stddev
  
} // end of Standard Deviation
