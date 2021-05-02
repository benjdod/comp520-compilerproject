  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L14
  4         HALT   (0)   
  5  L10:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8  L11:   CALL         puteol  
  9         RETURN (0)   0
 10  L12:   LOAD         -1[LB]
 11         JUMPIF (0)   L13
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         CALL         puteol  
 21         RETURN (0)   1
 22  L13:   LOADL        102
 23         CALL         put     
 24         LOADL        97
 25         CALL         put     
 26         LOADL        108
 27         CALL         put     
 28         LOADL        115
 29         CALL         put     
 30         LOADL        101
 31         CALL         put     
 32         CALL         puteol  
 33         RETURN (0)   1
 34  L14:   CALL         L11
 35         CALL         L11
 36         CALL         L11
 37         LOADL        0
 38         CALL         L12
 39         LOADL        4
 40         CALL         L10
 41         LOADL        1
 42         CALL         L12
 43         CALL         L11
 44         CALL         L11
 45         CALL         L11
 46         CALL         L11
 47         RETURN (0)   1
