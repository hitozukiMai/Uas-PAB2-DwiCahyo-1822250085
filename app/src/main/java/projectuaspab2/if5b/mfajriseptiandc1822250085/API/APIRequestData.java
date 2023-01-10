package projectuaspab2.if5b.mfajriseptiandc1822250085.API;

import projectuaspab2.if5b.mfajriseptiandc1822250085.Model.ResponseModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrive.php")
    Call<ResponseModel> ardRetriveData();

    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseModel> ardCreateData(
            @Field("nama") String nama,
            @Field("spesies") String spesies,
            @Field("habitat") String habitat,
            @Field("ilmiah") String ilmiah,
            @Field("pernapasan") String pernapasan

    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseModel> ardDeleteData(@Field("id") int id);

    @FormUrlEncoded
    @POST("get.php")
    Call<ResponseModel> ardGetData(@Field("id") int id);

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseModel> ardUpdateData(
            @Field("id") int id,
            @Field("nama") String nama,
            @Field("spesies") String spesies,
            @Field("habitat") String habitat,
            @Field("ilmiah") String ilmiah,
            @Field("pernapasan") String pernapasan

    );
}
