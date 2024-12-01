;I pledge that this submission is solely my work, and that I have neither given, nor received help from anyone
;Jonathan Rescorla
;49982379
        segment .data
a       db      1101b   ;multiplicand
b       db      1010b   ;multiplier
size    db      7       ;size of multiplier
result  dw      0       ;result

        segment .text
        global main
main:
        xor rax, rax            ;initializing registers
        xor rbx, rbx 
        xor rcx, rcx
        xor r8, r8
        movsx rax, byte[a]
        movsx rbx, byte[b]      ;moving multiplier to rbx for manipulation
        movsx rcx, byte[size]   ;moving size to rcx
        jmp dowhile
dowhile:
        bt rbx, 0               ;testing the first bit of the multiplier
        cmovc r8,rax            ;conditionally moving the modified multiplicand to r8
        add [result],r8         ;adding the modified multiplicand to result
        xor r8,r8               ;clearing r8 register
        shl rax, 1              ;shifting the multiplicand left one bit
        shr rbx, 1              ;shifting the multiplier right one bit
        dec rcx                 ;decrementing counter (size)
        test rcx, rcx           ;testing the counter for the zero flag
        jle dowhileexit         ;exiting the loop if counter = 0
        jmp dowhile             ;repeating the loop

dowhileexit:
        xor rax, rax            ;clearing registers and returning
        xor rbx, rbx
        xor rcx, rcx
        ret
