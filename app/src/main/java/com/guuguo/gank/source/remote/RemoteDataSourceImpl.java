//package com.guuguo.gank.source.remote;
//
//import com.future.awaker.data.BannerItem;
//import com.future.awaker.data.Comment;
//import com.future.awaker.data.NewDetail;
//import com.future.awaker.data.News;
//import com.future.awaker.data.Special;
//import com.future.awaker.data.SpecialDetail;
//import com.future.awaker.data.UserInfo;
//import com.future.awaker.network.AwakerApi;
//import com.future.awaker.network.HttpResult;
//import com.guuguo.gank.net.Service;
//
//import java.util.List;
//
//import io.reactivex.Flowable;
//import io.reactivex.schedulers.Schedulers;
//
//
//public class RemoteDataSourceImpl implements IRemoteDataSource {
//
//    private Service gankApi;
//
//    public RemoteDataSourceImpl(Service gankApi) {
//        this.gankApi = gankApi;
//    }
//
//    @Override
//    public Flowable<HttpResult<List<BannerItem>>> getBanner(String token, String advType) {
//        return gankApi.getBanner(token, advType)
//                .subscribeOn(Schedulers.io());
//    }
//
//    @Override
//    public Flowable<HttpResult<List<News>>> getNewList(String token, int page, int id) {
//        return gankApi.getNewList(token, page, id)
//                .subscribeOn(Schedulers.io());
//    }
//
//    @Override
//    public Flowable<HttpResult<List<Special>>> getSpecialList(String token, int page, int cat) {
//        return gankApi.getSpecialList(token, page, cat).subscribeOn(Schedulers.io());
//    }
//
//    @Override
//    public Flowable<HttpResult<NewDetail>> getNewDetail(String token, String newId) {
//        return gankApi.getNewDetail(token, newId)
//                .subscribeOn(Schedulers.io());
//    }
//
//    @Override
//    public Flowable<HttpResult<SpecialDetail>> getSpecialDetail(String token, String id) {
//        return gankApi.getSpecialDetail(token, id)
//                .subscribeOn(Schedulers.io());
//    }
//
//    @Override
//    public Flowable<HttpResult<List<Comment>>> getUpNewsComments(String token, String newId) {
//        return gankApi.getUpNewsComments(token, newId)
//                .subscribeOn(Schedulers.io());
//    }
//
//    @Override
//    public Flowable<HttpResult<List<Comment>>> getNewsComments(String token, String newId, int page) {
//        return gankApi.getNewsComments(token, newId, page)
//                .subscribeOn(Schedulers.io());
//    }
//
//    @Override
//    public Flowable<HttpResult<List<News>>> getHotviewNewsAll(String token, int page, int id) {
//        return gankApi.getHotviewNewsAll(token, page, id)
//                .subscribeOn(Schedulers.io());
//    }
//
//    @Override
//    public Flowable<HttpResult<List<News>>> getHotNewsAll(String token, int page, int id) {
//        return gankApi.getHotNewsAll(token, page, id)
//                .subscribeOn(Schedulers.io());
//    }
//
//    @Override
//    public Flowable<HttpResult<List<Comment>>> getHotComment(String token) {
//        return gankApi.getHotComment(token)
//                .subscribeOn(Schedulers.io());
//    }
//
//    @Override
//    public Flowable<HttpResult<Object>> sendNewsComment(String token, String newId, String content,
//                                                        String open_id, String pid) {
//        return gankApi.sendNewsComment(token, newId, content, open_id, pid)
//                .subscribeOn(Schedulers.io());
//    }
//
//    @Override
//    public Flowable<HttpResult<Object>> register(String token, String email, String nickname,
//                                                 String password) {
//        return gankApi.register(token, email, nickname, password)
//                .subscribeOn(Schedulers.io());
//    }
//
//    @Override
//    public Flowable<UserInfo> login(String token, String username, String password) {
//        return gankApi.login(token, username, password)
//                .subscribeOn(Schedulers.io());
//    }
//}
