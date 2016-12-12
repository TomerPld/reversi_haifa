from enum import Enum


class Winner(Enum):
    player_1 = 0
    player_2 = 1
    draw = 2


class PlayerOptions(Enum):
    Player = 0
    MinMax = 1
    MinMaxAlphaBeta = 2

    def get_next(current):
        return PlayerOptions((current.value + 1) % len(PlayerOptions.__members__.items()))


class MatrixValues(Enum):
    empty = 0
    player_1 = 1
    player_2 = 2
    neighbor = 3
