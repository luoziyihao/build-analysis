package com.coding.build.builder;

import com.coding.build.executor.Executor;
import com.coding.build.parser.Parser;
import com.coding.build.validator.Validator;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BuilderImpl implements Builder {

    protected Executor executor = null;


    public Parser jsonParser = null;
    public Validator validator = null;

    private final Logger logger = Logger.getLogger(this.getClass());

    public BuilderImpl() {

    }


    @Override
    public boolean build(Group target) {
        logger.debug("Going to build target: " + target);
        if (executor != null) {
            try {
                executor.setMavenHome(BuilderConfiguration.maven_home_path);
                executor.process(target);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public void setParser(Parser parser) {
        this.jsonParser = parser;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }


    @Override
    public List<Group> fetchGroups(String projectRoot) {
        List<Group> groups = new LinkedList<>();
        Path p = Paths.get(projectRoot);

        Predicate<Path> pfilter = path -> {
            return path.getFileName().toString().matches(".*group[0-9]+");
        };
        try {
            groups = Files.list(p).filter(pfilter).map(path -> {
                return jsonParser.parse(path.toString());
            }).filter(g -> g != null).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return groups;
    }


    @Override
    public boolean buildAll(List<Group> groups) {
        // TODO Auto-generated method stub
        return false;
    }


}
