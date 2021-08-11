package ml.bereket.githubresume.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Languages {
    Map<String, Double> languages = new HashMap<>();
}
