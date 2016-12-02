from tkinter import *

from board import PlayerOptions

class UI:
    def __init__(self, board):
        self.board = board
        
        self.master = Tk(className='Othello')
        self.frame = Frame(self.master)
        self.frame.grid(row=0,column=0)

        # set the board matrix
        self.buttons = []
        for i in range(12):
            for j in range(12):
                button = Button(self.frame, text="      ", command= lambda x=i, y=j: self.player_action(x,y))			
                button.grid(column=i,row=j)	
                self.buttons.append(button)	
        
        # set the options menu
        self.players = [PlayerOptions.Player, PlayerOptions.Player]
        self.player_buttons = []
        label_player_1 = Label(self.frame, text="Toggle Player 1")
        label_player_1.grid(column=12,row=0)	
        player_1_button = Button(self.frame, text=self.players[0].name, command=lambda x=0: self.toggle(x))
        player_1_button.grid(column=12,row=1)
        self.player_buttons.append(player_1_button)
        label_player_2 = Label(self.frame, text="Toggle Player 2")
        label_player_2.grid(column=12,row=2)	
        player_2_button = Button(self.frame, text=self.players[1].name, command=lambda x=1: self.toggle(x))
        player_2_button.grid(column=12,row=3)	
        self.player_buttons.append(player_2_button)
        
        
    def toggle(self, player_num):
        new = PlayerOptions.get_next(self.players[player_num])
        self.players[player_num] = new
        self.player_buttons[player_num].config(text=new.name)
        
    def loop(self):
        self.master.mainloop()
    
    def player_action(self,x,y):
        self.buttons[12 * x + y].config(bg="red")
        

    def draw_board(board):
        pass
        
    