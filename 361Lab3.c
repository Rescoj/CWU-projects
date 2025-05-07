#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int argvTemp = 4;
int position1 = 0;
int position2 = 0;
int iMoveNumber = 0;

void displayBoard(int a[argvTemp][argvTemp]);
void kt (int a [argvTemp][argvTemp], int x, int y);
int checkMoves(int a [argvTemp][argvTemp],int x, int y);
int checkMoveOne(int x, int y);
int checkMoveTwo(int x, int y);
int checkMoveThree(int x, int y);
int checkMoveFour(int x, int y);
int checkMoveFive(int x, int y);
int checkMoveSix(int x, int y);
int checkMoveSeven(int x, int y);
int checkMoveEight(int x, int y);
int checkArgC(int a);

int main(int argc, char *argv)
{
    //checkArgC(argc);

    int board [argvTemp][argvTemp];
    memset(&board,-1,sizeof(board));
    board [0][0] = 0;
    kt(board,0,0);
    displayBoard(board);

}
int checkArgC(int a) {
    if (a != 4) {
        exit(1);
    }
    else {
        return 1;
    }
}

void displayBoard (int a [argvTemp][argvTemp]){
        for (int i = 0; i < argvTemp; i++) {
        printf("\n");
        for (int j = 0; j < argvTemp; j++) {
            printf("%d\t",a[i][j]);
        }
    }
}

void kt (int a [argvTemp][argvTemp], int x, int y) {
    //printf("\n%d,%d",x,y);

    /*
    if (iMoveNumber == 11) {
        printf("\n%d,%d",x,y);
        printf("\n%d",checkMoves(a,x,y));
        printf("\n%d",checkMoveSeven(x,y));
        printf("\n%d",a[1][3]);
    }
    */

    if (checkMoves(a,x,y) == 0) {
        //printf("\nbacktrack");
        return;
    }

    if (checkMoveOne(x,y) && a[x-2][y+1] == -1) {
        //printf("\nmove one");
        if (iMoveNumber == 11) {
            //printf("\ncaught");
        }
        a[x-2][y+1] = ++iMoveNumber;
        kt(a, x-2, y+1);
    }
    if (checkMoveTwo(x,y) && a[x-2][y-1] == -1) {
        //printf("\nmove two");
        a[x-2][y-1] = ++iMoveNumber;
        kt(a, x-2, y-1);
    }
    if (checkMoveThree(x,y) && a[x+2][y+1] == -1) {
        //printf("\nmove three");
        a[x+2][y+1] = ++iMoveNumber;
        kt(a,x+2, y+1);
    }
    if (checkMoveFour(x,y) && a[x+2][y-1] == -1) {
        //printf("\nmove four");
        a[x+2][y-1] = ++iMoveNumber;
        kt(a, x+2, y-1);
    }
    if (checkMoveFive(x,y) && a[x-1][y-2] == -1) {
        //printf("\nmove five");
        a[x-1][y-2] = ++iMoveNumber;
        kt(a, x-1, y-2);
    }
    if (checkMoveSix(x,y) && a[x-1][y+2] == -1) {
        //printf("\nmove six");
        a[x-1][y+2] = ++iMoveNumber;
        kt(a, x-1, y+2);
    }
    if (checkMoveSeven(x,y) && a[x+1][y-2] == -1) {
        //printf("\n%d,%d",x,y);
        //printf("\nmove seven");
        a[x+1][y-2] = ++iMoveNumber;
        kt(a, x+1, y-2);
    }
    if (checkMoveEight(x,y) && a[x+1][y+2] == -1) {
        //printf("\nmove eight");
        a[x+1][y+2] = ++iMoveNumber;
        kt(a, x+1, y+2);
    }
    printf("\nbacktrack");

}

int checkMoves(int a [argvTemp][argvTemp],int x, int y) {
    int iMovesAvailable = 0;
    if (checkMoveOne(x,y) && a[x-2][y+1] == -1) {
        iMovesAvailable++;
    }
    if (checkMoveTwo(x,y) && a[x-2][y-1] == -1) {
        iMovesAvailable++;
    }
    if (checkMoveThree(x,y) && a[x+2][y+1] == -1) {
        iMovesAvailable++;
    }
    if (checkMoveFour(x,y) && a[x+2][y-1] == -1) {
        iMovesAvailable++;
    }
    if (checkMoveFive(x,y) && a[x-1][y-2] == -1) {
        iMovesAvailable++;
    }
    if (checkMoveSix(x,y) && a[x-1][y+2] == -1) {
        iMovesAvailable++;
    }
    if (checkMoveSeven(x,y) && a[x+1][y-2] == -1) {
        iMovesAvailable++;
    }
    if (checkMoveEight(x,y) && a[x+1][y+2] == -1) {
        iMovesAvailable++;
    }
    return iMovesAvailable;

}

//handles the case of up two and right one
int checkMoveOne(int x, int y) {
    if (x - 2 < 0 || y + 1 >= argvTemp) {
        return 0;
    }
    else {
        //printf("\nmove one");
        return 1;
    }
}

//handles the case of up two and left one
int checkMoveTwo(int x, int y) {
    if (x - 2 < 0 || y - 1 < 0) {
        return 0;
    }
    else {
        //printf("\nmove two");
        return 1;
    }
}

//handles the case of down two and right one
int checkMoveThree(int x, int y) {
    if (x + 2 >= argvTemp || y + 1 >= argvTemp) {
        return 0;
    }
    else {
        //printf("\nmove three");
        return 1;
    }

}

//handles the case of down two and left one
int checkMoveFour(int x, int y) {
    if (x + 2 >= argvTemp || y - 1 < 0) {
        return 0;
    }
    else {
        //printf("\nmove four");
        return 1;
    }

}

//handles the case of up one and left two
int checkMoveFive(int x, int y) {
    if (x - 1 < 0 || y - 2 < 0) {
        return 0;
    }
    else {
        //printf("\nmove five");
        return 1;
    }

}

//handles the case of up one and right two
int checkMoveSix(int x, int y) {
    if (x - 1 < 0 || y + 2 >= argvTemp) {
        return 0;
    }
    else {
        //printf("\nmove six");
        return 1;
    }

}

//handles the case of down one and left two
int checkMoveSeven(int x, int y) {
    if (x + 1 >= argvTemp || y - 2 < 0) {
        return 0;
    }
    else {
        //printf("\nmove seven");
        return 1;
    }

}

//handles the case of down one and right two
int checkMoveEight(int x, int y) {
    if (x + 1 >= argvTemp || y + 2 >= argvTemp) {
        return 0;
    }
    else {
        //printf("\nmove eight");
        return 1;
    }

}


