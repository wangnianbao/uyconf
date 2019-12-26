package com.broada.unbiz.common.genericdao.param;

/**
 * 封装like参数
 *
 * @author jay
 */
public class LikeParam {
    String word;

    public LikeParam(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}