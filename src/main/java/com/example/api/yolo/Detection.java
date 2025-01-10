package com.example.api.yolo;

public record Detection(String label, float[] bbox, float confidence) {

}
