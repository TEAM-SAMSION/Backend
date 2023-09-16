package com.pawith.imageapplication.service;

import java.util.List;
import java.util.function.Function;

public interface ImageDeleteUseCase<T> {
    void deleteImgList(List<T> imgUrlList, Function<T, String> imageUrlExtractor);
    public void deleteImg(String originImgUrl);
}
