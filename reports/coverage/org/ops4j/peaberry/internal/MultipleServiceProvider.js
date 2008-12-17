var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":509,"sl":33,"methods":[{"sl":42,"el":46,"sc":3},{"sl":48,"el":50,"sc":3},{"sl":70,"el":72,"sc":3}],"el":73,"name":"MultipleServiceProvider"},{"id":514,"sl":52,"methods":[{"sl":60,"el":63,"sc":5},{"sl":65,"el":67,"sc":5}],"el":68,"name":"MultipleServiceProvider.DirectProvider"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_11":{"methods":[{"sl":42},{"sl":60},{"sl":70}],"name":"configure","statements":[{"sl":44},{"sl":45},{"sl":62},{"sl":71}],"pass":true},"test_9":{"methods":[{"sl":42}],"name":"configure","statements":[{"sl":44},{"sl":45}],"pass":true},"test_15":{"methods":[{"sl":65}],"name":"testDirectServiceInjection","statements":[{"sl":66}],"pass":true},"test_30":{"methods":[{"sl":42}],"name":"configure","statements":[{"sl":44},{"sl":45}],"pass":true},"test_31":{"methods":[{"sl":42}],"name":"configure","statements":[{"sl":44},{"sl":45}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [11, 31, 30, 9], [], [11, 31, 30, 9], [11, 31, 30, 9], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [11], [], [11], [], [], [15], [15], [], [], [], [11], [11], [], []]
