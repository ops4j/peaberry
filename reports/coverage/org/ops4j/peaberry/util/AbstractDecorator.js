var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":1377,"sl":33,"methods":[{"sl":36,"el":45,"sc":3},{"sl":39,"el":43,"sc":7}],"el":55,"name":"AbstractDecorator"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_3":{"methods":[{"sl":36},{"sl":39}],"name":"testServiceInterception","statements":[{"sl":37},{"sl":42}],"pass":true},"test_26":{"methods":[{"sl":36},{"sl":39}],"name":"testServiceInjection","statements":[{"sl":37},{"sl":42}],"pass":true},"test_19":{"methods":[{"sl":36},{"sl":39}],"name":"testDecoratedServiceInjection","statements":[{"sl":37},{"sl":42}],"pass":true},"test_44":{"methods":[{"sl":36},{"sl":39}],"name":"testDecoratorChaining","statements":[{"sl":37},{"sl":42}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [3, 44, 19, 26], [3, 44, 19, 26], [], [3, 44, 19, 26], [], [], [3, 44, 19, 26], [], [], [], [], [], [], [], [], [], [], [], [], []]
