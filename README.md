[ ![Download](https://api.bintray.com/packages/m1noon/maven/Disco/images/download.svg) ](https://bintray.com/m1noon/maven/Disco/_latestVersion)
# Disco
scroll animation library for Android

# Image
![demo_simple]
![demo_complex]

# Gradle

```
compile 'com.minoon:disco:0.1.2'
```

# How To Use
```
Disco disco = new Disco();
disco.addScrollView(mRecyclerView);

disco.addScrollObserver(mToolbar, disco.getScrollChoreographyBuilder()
  .onScrollVertical()
  .multiplier(0.7f)
  .alpha(1f, 0.7f)
  .end()
  .build()
);

disco.addViewObserver(mToolbar, mButton, disco.getViewChaseChoreographyBuilder()
  .onChange(ViewParam.TRANSLATION_Y, 0, -200)
  .alpha(0f, 1f)
  .end()
  .build()
);

```

# License
```
Copyright (C) 2015 m1noon
Copyright (C) 2013 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[demo_simple]:https://github.com/m1noon/Disco/blob/master/art/demo_simple.gif
[demo_complex]:https://github.com/m1noon/Disco/blob/master/art/demo_complex.gif
