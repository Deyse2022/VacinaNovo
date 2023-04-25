package deyse.souza.appvacina.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.model.Pessoa;

public class AdapterPessoas extends RecyclerView.Adapter <AdapterPessoas.MyViewHolder> {

    private List<Pessoa> pessoas;
    private Context context;



    public AdapterPessoas(List<Pessoa> pessoas, Context context){
        this.pessoas = pessoas;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome;
        TextView dtnasc;
        TextView status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textNomePessoa);
            dtnasc = itemView.findViewById(R.id.textDtNasc);
            status = itemView.findViewById(R.id.textStatus);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pessoas,parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPessoas.MyViewHolder holder, int position) {
        Pessoa pessoa = pessoas.get(position);
        holder.nome.setText(pessoa.getNome());
        holder.dtnasc.setText(pessoa.getDtnascimento());
        holder.status.setText("Vencida");
    }

    @Override
    public int getItemCount() {
        return pessoas.size();
    }




}

