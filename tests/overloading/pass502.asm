  0         PUSH         1
  1         LOADL        0
  2         CALL         newarr  
  3         CALL         L13
  4         HALT   (0)   
  5  L11:   LOAD         -1[LB]
  6         CALL         putintnl
  7         RETURN (0)   1
  8         CALL         puteol  
  9         RETURN (0)   0
 10         LOAD         -1[LB]
 11         JUMPIF (0)   L12
 12         LOADL        116
 13         CALL         put     
 14         LOADL        114
 15         CALL         put     
 16         LOADL        117
 17         CALL         put     
 18         LOADL        101
 19         CALL         put     
 20         RETURN (0)   1
 21  L12:   LOADL        102
 22         CALL         put     
 23         LOADL        97
 24         CALL         put     
 25         LOADL        108
 26         CALL         put     
 27         LOADL        115
 28         CALL         put     
 29         LOADL        101
 30         CALL         put     
 31         CALL         puteol  
 32         RETURN (0)   1
 33         LOAD         -1[LB]
 34         JUMPIF (0)   L10
 35         LOAD         -1[LB]
 36         CALL         putarr  
 37         CALL         puteol  
 38         RETURN (0)   1
 39  L13:   CALL         L17
 40         LOADL        1
 41         LOADL        15
 42         CALL         L14
 43         POP          1
 44         LOADL        2
 45         CALL         L16
 46         POP          1
 47         LOADL        1
 48         LOADL        1
 49         CALL         L14
 50         CALL         L11
 51         RETURN (0)   1
 52  L14:   LOAD         -2[LB]
 53         JUMPIF (0)   L15
 54         LOAD         -1[LB]
 55         CALL         neg     
 56         RETURN (1)   2
 57         JUMP         L16
 58  L15:   LOAD         -1[LB]
 59         LOADL        1
 60         CALL         add     
 61         RETURN (1)   2
 62  L16:   LOAD         -1[LB]
 63         LOADL        17
 64         CALL         add     
 65         RETURN (1)   1
 66  L17:   RETURN (0)   0
