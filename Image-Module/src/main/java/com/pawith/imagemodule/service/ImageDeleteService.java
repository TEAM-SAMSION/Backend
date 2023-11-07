package com.pawith.imagemodule.service;

import java.util.List;
import java.util.function.Function;

public interface ImageDeleteService<T> {
    void deleteImgList(List<T> imgUrlList, Function<T, String> imageUrlExtractor);
    public void deleteImg(String originImgUrl);
}
