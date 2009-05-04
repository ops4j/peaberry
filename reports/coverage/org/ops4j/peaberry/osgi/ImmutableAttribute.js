var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1203,"sl":26,"methods":[{"sl":32,"el":35,"sc":3},{"sl":37,"el":39,"sc":3},{"sl":41,"el":43,"sc":3},{"sl":45,"el":47,"sc":3},{"sl":49,"el":57,"sc":3},{"sl":59,"el":62,"sc":3},{"sl":64,"el":66,"sc":3}],"el":67,"name":"ImmutableAttribute"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_67":{"methods":[{"sl":32},{"sl":37},{"sl":41},{"sl":45},{"sl":49},{"sl":59},{"sl":64}],"name":"testImmutableAttributes","statements":[{"sl":33},{"sl":34},{"sl":38},{"sl":42},{"sl":46},{"sl":52},{"sl":53},{"sl":54},{"sl":56},{"sl":61},{"sl":65}],"pass":true},"test_3":{"methods":[{"sl":32},{"sl":37}],"name":"testServiceInterception","statements":[{"sl":33},{"sl":34},{"sl":38}],"pass":true},"test_26":{"methods":[{"sl":32},{"sl":37}],"name":"testServiceInjection","statements":[{"sl":33},{"sl":34},{"sl":38}],"pass":true},"test_19":{"methods":[{"sl":32},{"sl":37}],"name":"testDecoratedServiceInjection","statements":[{"sl":33},{"sl":34},{"sl":38}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [3, 67, 19, 26], [3, 67, 19, 26], [3, 67, 19, 26], [], [], [3, 67, 19, 26], [3, 67, 19, 26], [], [], [67], [67], [], [], [67], [67], [], [], [67], [], [], [67], [67], [67], [], [67], [], [], [67], [], [67], [], [], [67], [67], [], []]
