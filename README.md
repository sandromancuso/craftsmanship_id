### Skeleton Scala Project

#### Generatera Idea / Eclipse files

sbt    
test    
gen-idea   
eclipse



## Build & Run ##

```sh
$ cd skeletonapp
$ ./sbt
> container:start
> ~ ;copy-resources;aux-compile (automatic code reload)
> ~;container:start; container:reload / (automatically code reload, if the above does not work)
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.
