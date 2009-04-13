var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"id":2130,"sl":33,"methods":[{"sl":36,"el":45,"sc":3},{"sl":39,"el":43,"sc":7}],"el":55,"name":"AbstractDecorator"}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1":{"methods":[{"sl":36},{"sl":39}],"name":"testServiceInterception","statements":[{"sl":37},{"sl":42}],"pass":true},"test_3":{"methods":[{"sl":36},{"sl":39}],"name":"testServiceInjection","statements":[{"sl":37},{"sl":42}],"pass":true},"test_74":{"methods":[{"sl":36},{"sl":39}],"name":"testDecoratedServiceInjection","statements":[{"sl":37},{"sl":42}],"pass":true},"test_9":{"methods":[{"sl":36},{"sl":39}],"name":"testDecoratorChaining","statements":[{"sl":37},{"sl":42}],"pass":true}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [1, 3, 74, 9], [1, 3, 74, 9], [], [1, 3, 74, 9], [], [], [1, 3, 74, 9], [], [], [], [], [], [], [], [], [], [], [], [], []]
