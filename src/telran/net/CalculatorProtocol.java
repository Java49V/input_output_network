//package telran.net;
//
//public class CalculatorProtocol implements ApplProtocol {
//
//	@Override
//	public Response getResponse(Request request) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}

package telran.net;

import java.io.Serializable;

public class CalculatorProtocol implements ApplProtocol {

    @Override
    public Response getResponse(Request request) {
        String requestType = request.requestType();
        Serializable requestData = request.requestData();

        if (requestType.equals("add")) {
            double[] data = (double[]) requestData;
            double result = data[0] + data[1];
            return new Response(ResponseCode.OK, result);
        } else if (requestType.equals("minus")) {
            double[] data = (double[]) requestData;
            double result = data[0] - data[1];
            return new Response(ResponseCode.OK, result);
        } else if (requestType.equals("multiply")) {
            double[] data = (double[]) requestData;
            double result = data[0] * data[1];
            return new Response(ResponseCode.OK, result);
        } else if (requestType.equals("divide")) {
            double[] data = (double[]) requestData;
            if (data[1] == 0) {
                return new Response(ResponseCode.WRONG_DATA, "Division by zero");
            }
            double result = data[0] / data[1];
            return new Response(ResponseCode.OK, result);
        }

        return new Response(ResponseCode.WRONG_TYPE, "Unknown operation");
    }
}
