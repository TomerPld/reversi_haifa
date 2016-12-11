import argparse

from ui import *
from board import *
from heuristic import *

# Reversi 12x12
# AI vs AI (2 different heuristic funcions)
# AI vs Player
# Player vs Player
# can change algorithm and depth every step
# write to a file the last coordinate placed:
#      _______ 
# 1   |       |
# ... |       |
# 12  |_______|
#     A ... L
# so the step is:
# ([WB], [A-L][1-12])
# whre W/B states who's turn (black writes W, white writes B)
# This way we can play against other students


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('-d', '--depth', dest='max_depth', default=4, help='Maximal depth')
    # parser.add_argument('image_file', help='image file name full path')
    args = parser.parse_args()

    board = Board(heuristic_funcion, heuristic_funcion2)
    ui = UI(board)
    ui.loop()
    print()
    
    
    
    
    
    

if __name__ == '__main__':
    main()