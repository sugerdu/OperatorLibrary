package org.osv.eventdb;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.coprocessor.Batch;
import org.apache.hadoop.hbase.ipc.BlockingRpcCallback;
import org.osv.eventdb.OperatorLibrary.OperatorLibraryRequest;
import org.osv.eventdb.OperatorLibrary.OperatorLibraryResponse;
import org.osv.eventdb.OperatorLibrary.OperatorLibraryService;

/**
 * @author DZR
 * 说明：hbase协处理器endpooint的客户端代码
 * 功能：从服务端获取对hbase表指定列的数据的求和结果
 */

public class OperatorLibraryClient {
	//Gamma函数
	public static List<Double> Gamma(String column,Connection conn) throws Throwable{
	     // 获取表
        HTable table = (HTable) conn.getTable(TableName.valueOf("Coprocessor_Table"));
        // 设置请求对象
        final OperatorLibraryRequest request;
        request=OperatorLibraryRequest.newBuilder().setColumn(column).build();   
        // 获得返回值
        Map<byte[], List<Double>> result = table.coprocessorService(OperatorLibraryService.class, null, null, 
                new Batch.Call<OperatorLibraryService, List<Double>>() {

                    @Override
                    public List<Double> call(OperatorLibraryService service) throws IOException {
                        BlockingRpcCallback<OperatorLibraryResponse> rpcCallback = new BlockingRpcCallback<OperatorLibraryResponse>();
                        service.getGamma(null, request, rpcCallback);
                        OperatorLibraryResponse response = (OperatorLibraryResponse) rpcCallback.get();
                        return response.getResultList();
                    }
        });
        // 将返回值进行迭代合并
        List<Double>  GammaResult=new ArrayList<>();
        for (List<Double> v : result.values()) { 
        		GammaResult.addAll(v);	   	
        }
        // 关闭资源
        table.close();
        return GammaResult;
	}
	//Erf函数
	public static List<Double> Erf(String column,Connection conn) throws Throwable{
	     // 获取表
       HTable table = (HTable) conn.getTable(TableName.valueOf("Coprocessor_Table"));
       // 设置请求对象;
       final OperatorLibraryRequest request;
       request=OperatorLibraryRequest.newBuilder().setColumn(column).build(); 
       // 获得返回值
       Map<byte[], List<Double>> result = table.coprocessorService(OperatorLibraryService.class, null, null, 
               new Batch.Call<OperatorLibraryService, List<Double>>() {

                   @Override
                   public List<Double> call(OperatorLibraryService service) throws IOException {
                       BlockingRpcCallback<OperatorLibraryResponse> rpcCallback = new BlockingRpcCallback<OperatorLibraryResponse>();
                       service.getErf(null, request, rpcCallback);
                       OperatorLibraryResponse response = (OperatorLibraryResponse) rpcCallback.get();
                       return response.getResultList();
                   }
       });
       // 将返回值进行迭代合并
       List<Double>  ErfResult=new ArrayList<>();
       for (List<Double> v : result.values()) {
    		   ErfResult.addAll(v);      
       }
       // 关闭资源
       table.close();
       return ErfResult;
	}
	//Sum函数
	public static Double Sum(String column,Connection conn)throws Throwable{
	     // 获取表
        HTable table = (HTable) conn.getTable(TableName.valueOf("Coprocessor_Table"));
        // 设置请求对象
        final OperatorLibraryRequest request;
        request=OperatorLibraryRequest.newBuilder().setColumn(column).build(); 
        // 获得返回值
		Map<byte[], List<Double>> result = table.coprocessorService(OperatorLibraryService.class, null, null, 
                new Batch.Call<OperatorLibraryService, List<Double>>() {
                    @Override
                    public List<Double> call(OperatorLibraryService service) throws IOException {
                        BlockingRpcCallback<OperatorLibraryResponse> rpcCallback = new BlockingRpcCallback<OperatorLibraryResponse>();
                        service.getSum(null, request, rpcCallback);
                        OperatorLibraryResponse response = (OperatorLibraryResponse) rpcCallback.get();
                        return response.getResultList() ;
                    }
        });
        // 将返回值进行迭代相加
		double sumResult=0.0;
        for (List<Double> v1 : result.values()) {
        	for(Double v2:v1)
            {
             		sumResult=sumResult+v2;
            }               	
        }
        // 关闭资源
        table.close();
        return sumResult;		
	}
	//Average函数
	public static Double Ave(String column,Connection conn)throws Throwable{
	     // 获取表
        HTable table = (HTable) conn.getTable(TableName.valueOf("Coprocessor_Table"));
        // 设置请求对象
        final OperatorLibraryRequest request;
        request=OperatorLibraryRequest.newBuilder().setColumn(column).build(); 
        // 获得返回值
		Map<byte[], List<Double>> result = table.coprocessorService(OperatorLibraryService.class, null, null, 
                new Batch.Call<OperatorLibraryService, List<Double>>() {
                    @Override
                    public List<Double> call(OperatorLibraryService service) throws IOException {
                        BlockingRpcCallback<OperatorLibraryResponse> rpcCallback = new BlockingRpcCallback<OperatorLibraryResponse>();
                        service.getAve(null, request, rpcCallback);
                        OperatorLibraryResponse response = (OperatorLibraryResponse) rpcCallback.get();
                        return response.getResultList();
                    }
        });
        // 将返回值进行迭代相加
		Double sum=0.0;
		Double ave=0.0;
        for (List<Double> v1 : result.values()) {
        	for(Double v2:v1)
            {
             	sum=sum+v2;
            }       		
        }      
        ave=sum/result.size();            
        // 关闭资源
        table.close();
        return ave;		
	}
	//Max函数
	public static Double Max(String column,Connection conn)throws Throwable{
	     // 获取表
        HTable table = (HTable) conn.getTable(TableName.valueOf("Coprocessor_Table"));
        // 设置请求对象
        final OperatorLibraryRequest request;
        request=OperatorLibraryRequest.newBuilder().setColumn(column).build(); 
        // 获得返回值
		Map<byte[], Double> result = table.coprocessorService(OperatorLibraryService.class, null, null, 
                new Batch.Call<OperatorLibraryService, Double>() {
                    @Override
                    public Double call(OperatorLibraryService service) throws IOException {
                        BlockingRpcCallback<OperatorLibraryResponse> rpcCallback = new BlockingRpcCallback<OperatorLibraryResponse>();
                        service.getMax(null, request, rpcCallback);
                        OperatorLibraryResponse response = (OperatorLibraryResponse) rpcCallback.get();
                        return response.getResult(0) ;
                    }
        });
        // 将返回值进行迭代相加
		List<Double> MaxList =new ArrayList<Double>();
        for (Double v : result.values()) {
        		MaxList.add(v);        	     	
        }
        // 关闭资源
        table.close();
        return Collections.max(MaxList);		
	}
	//Min函数
	public static Double Min(String column,Connection conn)throws Throwable{
	     // 获取表
       HTable table = (HTable) conn.getTable(TableName.valueOf("Coprocessor_Table"));
       // 设置请求对象
       final OperatorLibraryRequest request;
       request=OperatorLibraryRequest.newBuilder().setColumn(column).build(); 
       // 获得返回值
		Map<byte[], Double> result = table.coprocessorService(OperatorLibraryService.class, null, null, 
               new Batch.Call<OperatorLibraryService, Double>() {
                   @Override
                   public Double call(OperatorLibraryService service) throws IOException {
                       BlockingRpcCallback<OperatorLibraryResponse> rpcCallback = new BlockingRpcCallback<OperatorLibraryResponse>();
                       service.getMin(null, request, rpcCallback);
                       OperatorLibraryResponse response = (OperatorLibraryResponse) rpcCallback.get();
                       return response.getResult(0) ;
                   }
       });
       // 将返回值进行迭代相加
	   List<Double> MinList =new ArrayList<Double>();
       for (Double v : result.values()) {
       	MinList.add(v);
       }
       // 关闭资源
       table.close();
       return Collections.min(MinList);		
	}	
}


